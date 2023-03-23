import cn.edu.zust.vod.utils.AliyunVodSDKUtil;
import cn.edu.zust.vod.utils.VodConstantPropertiesUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestVod {
    @Test
    public static void main(String[] args) throws ClientException {
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(VodConstantPropertiesUtil.ACCESS_KEY_ID,
                VodConstantPropertiesUtil.ACCESS_KEY_SECRET);

        // 创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("5363abe4a8f04b5f89522110b5551298");
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }

}
