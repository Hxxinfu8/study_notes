package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileTypeUtil {
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    private FileTypeUtil() {
    }

    static {
        getAllFileType(); // 初始化文件类型信息
    }

    /**
     * Discription:[getAllFileType,常见文件头信息]
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ff", "jpg"); // JPEG (jpg)
        FILE_TYPE_MAP.put("89504e", "png"); // PNG (png)
        FILE_TYPE_MAP.put("474946", "gif"); // GIF (gif)
        FILE_TYPE_MAP.put("49492a", "tif"); // TIFF (tif)
        FILE_TYPE_MAP.put("424d22", "bmp"); // 16色位图(bmp)
        FILE_TYPE_MAP.put("424d82", "bmp"); // 24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e", "bmp"); // 256色位图(bmp)
        FILE_TYPE_MAP.put("414331", "dwg"); // CAD (dwg)
        FILE_TYPE_MAP.put("3c2144", "html"); // HTML (html)
        FILE_TYPE_MAP.put("3c2164", "htm"); // HTM (htm)
        FILE_TYPE_MAP.put("48544d", "css"); // css
        FILE_TYPE_MAP.put("696b2e", "js"); // js
        FILE_TYPE_MAP.put("7b5c72", "rtf"); // Rich Text Format (rtf)
        FILE_TYPE_MAP.put("384250", "psd"); // Photoshop (psd)
        FILE_TYPE_MAP.put("46726f", "eml"); // Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11", "doc"); // MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11", "vsd"); // Visio 绘图
        FILE_TYPE_MAP.put("537461", "mdb"); // MS Access (mdb)
        FILE_TYPE_MAP.put("252150", "ps");
        FILE_TYPE_MAP.put("255044", "pdf"); // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d", "rmvb"); // rmvb/rm相同
        FILE_TYPE_MAP.put("464c56", "flv"); // flv与f4v相同
        FILE_TYPE_MAP.put("000000", "mp4");
        FILE_TYPE_MAP.put("494433", "mp3");
        FILE_TYPE_MAP.put("000001", "mpg"); //
        FILE_TYPE_MAP.put("3026b2", "wmv"); // wmv与asf相同
        FILE_TYPE_MAP.put("524946", "wav"); // Wave (wav)
        FILE_TYPE_MAP.put("524946", "avi");
        FILE_TYPE_MAP.put("4d5468", "mid"); // MIDI (mid)
        FILE_TYPE_MAP.put("504b03", "zip");
        FILE_TYPE_MAP.put("526172", "rar");
        FILE_TYPE_MAP.put("235468", "ini");
        FILE_TYPE_MAP.put("504b03", "jar");
        FILE_TYPE_MAP.put("4d5a90", "exe");// 可执行文件
        FILE_TYPE_MAP.put("3c2540", "jsp");// jsp文件
        FILE_TYPE_MAP.put("4d616e", "mf");// MF文件
        FILE_TYPE_MAP.put("3c3f78", "xml");// xml文件
        FILE_TYPE_MAP.put("494e53", "sql");// xml文件
        FILE_TYPE_MAP.put("706163", "java");// java文件
        FILE_TYPE_MAP.put("406563", "bat");// bat文件
        FILE_TYPE_MAP.put("1f8b08", "gz");// gz文件
        FILE_TYPE_MAP.put("6c6f67", "properties");// bat文件
        FILE_TYPE_MAP.put("cafeba", "class");// bat文件
        FILE_TYPE_MAP.put("495453", "chm");// bat文件
        FILE_TYPE_MAP.put("040000", "mxp");// bat文件
        FILE_TYPE_MAP.put("504b03", "docx");// docx文件
        FILE_TYPE_MAP.put("d0cf11", "wps");// WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("643130", "torrent");
    }

    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据制定文件的文件头判断其文件类型
     *
     * @param filePaht
     * @return
     */
    public static String getFileType(byte[] bytes) {
        String res = null;
        String fileCode = bytesToHexString(bytes);
        // 这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
        Iterator<String> iterator = FILE_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                res = FILE_TYPE_MAP.get(key);
                break;
            }
        }
        return res;
    }
}
