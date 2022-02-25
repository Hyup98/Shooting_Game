package Network;

import Game.Language;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Translator {
    private Language MyLag;
    private Language inputLag;
    private String clientID;
    private String clientPW;
    private String text;
    private String str_response;
    private Object object;
    private JSONObject jsonObject;
    private JSONParser parser;

    public Translator(Language myLag) throws ProtocolException {
        parser = new JSONParser();
        this.MyLag = myLag;


        System.out.println("번역기 생성 완료");
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

    public String translate(String input, Language inputLag) throws IOException {
        this.inputLag = inputLag;
        //inputData encode//
        try {
            text = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }
        URL url = new URL("https://openapi.naver.com/v1/papago/n2mt");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        clientID = "CE82OwiEg5teK2wkzyzR";//애플리케이션 클라이언트 아이디값";
        clientPW = "GD8CTer9VJ";//애플리케이션 클라이언트 시크릿값";
        connection.setRequestMethod("POST");
        connection.setRequestProperty("X-Naver-Client-Id", clientID);
        connection.setRequestProperty("X-Naver-Client-Secret", clientPW);
        connection.setDoOutput(true);

        String request = makePostParams(input);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(request.getBytes());
            wr.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                //return readBody(connection.getInputStream());
                str_response = readBody(connection.getInputStream());
                object = parser.parse(str_response);

                jsonObject = (JSONObject) object;
                object = jsonObject.get("message");

                jsonObject = (JSONObject) object;
                object = jsonObject.get("result");

                jsonObject = (JSONObject) object;
                str_response =(String) jsonObject.get("translatedText");
                wr.close();
                return str_response;
            } else {  // 에러 응답
                System.out.println("번역 최종본 에러1");
                return readBody(connection.getErrorStream());
            }
        } catch (IOException | ParseException e) {
            System.out.println("번역 최종본 에러2");
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
            streamReader.close();
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public String makePostParams(String string) {
        String my = "";
        String target = "";
        switch (MyLag) {
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
        switch (inputLag) {
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
