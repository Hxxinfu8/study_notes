package com.company;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesRequest;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.SensitiveWordsRecognitionRequest;
import com.tencentcloudapi.nlp.v20190408.models.SensitiveWordsRecognitionResponse;

public class Tencent {
    public static void main(String[] args) throws TencentCloudSDKException {
        Credential cred = new Credential("AKIDykFYaLVZ2D2f3BoeyayDAq53sUu3qg6j",
                "ZsoVwBArd8MJ4YnktlMgjWZMs76oHSpl");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
        NlpClient client = new NlpClient(cred, "ap-guangzhou", clientProfile);


        // 实例化一个请求对象
        SensitiveWordsRecognitionRequest request = new SensitiveWordsRecognitionRequest();
        request.setText("卧槽尼玛");
        // 通过client对象调用想要访问的接口，需要传入请求对象
        SensitiveWordsRecognitionResponse response = client.SensitiveWordsRecognition(request);

        // 输出json格式的字符串回包
        System.out.println(DescribeZonesRequest.toJsonString(response));
    }
}
