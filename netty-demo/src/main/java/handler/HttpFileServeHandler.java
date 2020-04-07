package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class HttpFileServeHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private static final String URL = "E:";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request){
        // 浏览器访问会同时请求一次favicon.ico
        if (request.uri().equals("/favicon.ico")) {
            return;
        }

        if (request.decoderResult().isFailure()) {
            sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);
            return;
        }

        if (!request.method().equals(HttpMethod.GET)) {
            sendError(channelHandlerContext, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        String uri = request.uri();
        String path = sanitizeUri(uri);

        if (path == null) {
            sendError(channelHandlerContext, HttpResponseStatus.FORBIDDEN);
            return;
        }

        System.err.println("打开" + path);
        File file = new File(path);

        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendList(channelHandlerContext, file);
            } else {
                sendRedirect(channelHandlerContext, uri + "/");
            }
            return;
        }

        if (!file.isFile()) {
            sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);
            return;
        }
        RandomAccessFile accessFile;
        try {
            accessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);
            e.printStackTrace();
            return;
        }
        try {
            long fileLength = accessFile.length();
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
            MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
            headers.set(HttpHeaderNames.CONTENT_TYPE, typeMap.getContentType(file));
            if (HttpUtil.isKeepAlive(request)) {
                headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            channelHandlerContext.write(response);
            ChannelFuture channelFuture = channelHandlerContext.write(new ChunkedFile(accessFile, 0, fileLength, 8192), channelHandlerContext.newProgressivePromise());
            // 文件下载事件监听
            channelFuture.addListener(new ChannelProgressiveFutureListener() {

                @Override
                public void operationComplete(ChannelProgressiveFuture channelProgressiveFuture) throws Exception {
                    System.err.println("Transfer complete");
                }

                @Override
                public void operationProgressed(ChannelProgressiveFuture channelProgressiveFuture, long l, long l1) throws Exception {
                    if (l1 < 0) {
                        System.err.println("Transfer Progress" + l);
                    } else {
                        System.err.println("Transfer Progress" + l + "/" + l1);
                    }
                }
            });
            ChannelFuture lastFuture = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!HttpUtil.isKeepAlive(request)) {
                lastFuture.addListener(ChannelFutureListener.CLOSE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String sanitizeUri(String uri) {
        // uri解码
        try {
            uri = URLDecoder.decode(uri, CharsetUtil.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            try {
                uri = URLDecoder.decode(uri, CharsetUtil.ISO_8859_1.name());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }

        if (!uri.startsWith("/")) {
            return null;
        }

        // 替换文件路径分隔符，适用不同操作系统
        uri = uri.replace("/", File.separator);

        if (uri.contains("." + File.separator)
                || uri.startsWith(".") || uri.endsWith(".")
                || uri.contains(File.separator + ".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }

        return URL + File.separator + uri;
    }

    private void sendList(ChannelHandlerContext ctx, File file) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK
        );
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\r\n")
                .append("<html lang=\"en\">\r\n")
                .append("<head>")
                .append("    <meta charset=\"UTF-8\">\r\n")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n")
                .append("    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\r\n")
                .append("    <title>Document</title>\r\n")
                .append("</head>\r\n")
                .append("<body>\r\n")
                .append("    <h3>")
                .append(file.getPath())
                .append("目录</h3>\n")
                .append("        <ul>\r\n")
                .append("            <li><a href=\"../\">..</a></li>\r\n");
        for (File f : file.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            stringBuilder.append("            <li><a href=\"")
                    .append(f.getName())
                    .append("\">")
                    .append(f.getName())
                    .append("</a></li>\r\n");
        }
        stringBuilder.append("        </ul>\r\n")
                .append("    </h3>\r\n")
                .append("</body>\r\n")
                .append("</html>\r\n");
        ByteBuf buf = Unpooled.copiedBuffer(stringBuilder, CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        buf.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    // 重定向
    private void sendRedirect(ChannelHandlerContext ctx, String uri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, uri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("取消下载文件");
        ctx.close();
    }
}
