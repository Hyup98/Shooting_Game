package Network;

import Game.Language;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Translator {
    private Language lag;
    private Language targetLag;
    private String clientID;
    private String clientPW;
    private String text;
    private HttpURLConnection connection;

    public Translator(Language myLag) {
        this.lag = myLag;
        clientID = "CE82OwiEg5teK2wkzyzR";//애플리케이션 클라이언트 아이디값";
        clientPW = "GD8CTer9VJ";//애플리케이션 클라이언트 시크릿값";
        connection = connect("https://openapi.naver.com/v1/papago/n2mt");
        System.out.println("번역기 생성 완료");
        switch (lag) {
            case ENG:
                System.out.println("번역기 생성 완료" + "en");
                break;
            case JAP:
                System.out.println("번역기 생성 완료" + "ja");
                break;
            case ITAL:
                System.out.println("번역기 생성 완료" + "it");
                break;
            case CHAIN:
                System.out.println("번역기 생성 완료" + "zh-CN");
                break;
            case KOR:
                System.out.println("번역기 생성 완료" + "ko");
                break;

            default:
                break;
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    public String translate(String input, Language targetLag) throws ProtocolException {
        this.targetLag = targetLag;
        try {
            text = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientID);
        requestHeaders.put("X-Naver-Client-Secret", clientPW);
        String request = makePostParams(input);

        connection.setRequestMethod("POST");
        for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        connection.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(request.getBytes());
            wr.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(connection.getInputStream());
            } else {  // 에러 응답
                return readBody(connection.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public String makePostParams(String string) {
        String my = "";
        String target = "";
        switch (lag) {
            case ENG:
                my = "en";
                break;
            case JAP:
                my = "ja";
                break;
            case ITAL:
                my = "it";
                break;
            case CHAIN:
                my = "zh-CN";
                break;
            case KOR:
                my = "ko";
                break;
            default:
                break;
        }
        switch (targetLag) {
            case ENG:
                target = "en";
                break;
            case JAP:
                target = "ja";
                break;
            case ITAL:
                target = "it";
                break;
            case CHAIN:
                target = "zh-CN";
                break;
            case KOR:
                target = "ko";
                break;
            default:
                break;
        }
        return  "source=" + target +"&target="+ my +"&text=" + text;
    }

}
