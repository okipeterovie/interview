package com.interviewtest.line.remita;
import com.google.gson.Gson;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HashUtil {
    public enum HashType {
        MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"),SHA512("SHA-512");
        String algoName;

        HashType(String name) {
            algoName = name;
        }

        public String getAlgoName() {
            return algoName;
        }
    }

    public static String getHash(File file, HashType hashType) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance(hashType.algoName);
        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();
        // convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        // close the fileInputStream
        fis.close();

        return sb.toString();
    }

    public static String getHash(String data, HashType hashType) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance(hashType.algoName);
        md.update(data.getBytes("UTF-8"));

        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void trustCertificate(String url2) {

        HttpURLConnection httpURLConnection = null;

        URL url = null;
        try {
//            RemittaStatusResponse responseHandle = new RemittaStatusResponse();

            /* Start of Fix */
            TrustManager[] trustAllCerts2 = new X509TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

            }};


            SSLContext sc2 = SSLContext.getInstance("SSL");
            sc2.init(null, trustAllCerts2, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc2.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid2 = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid2);

            /* End of the fix*/

            url = new URL(url2);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            //To get response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String response2 = null;
            StringBuilder builder = new StringBuilder();
            while ((response2 = bufferedReader.readLine()) != null) {
                builder.append(response2);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            System.out.println("builder>>> " + builder.toString());


        } catch (MalformedURLException e) {
//        	rd.addFlashAttribute("msg","Payment gateway error.");
            e.printStackTrace();
        } catch (ProtocolException e) {
//        	rd.addFlashAttribute("msg","Payment gateway error..");
            e.printStackTrace();
        } catch (IOException e) {
//        	rd.addFlashAttribute("msg","Payment gateway error...");
            e.printStackTrace();
        }  catch (Exception e) {
//        	rd.addFlashAttribute("msg","Payment gateway error....");
            e.printStackTrace();
        }
    }

    private static final Gson GSON = new Gson();
    public static <T> T fromJson(String data, Class<T> tClass) {
        return GSON.fromJson(data, tClass);
    }
    
    


}
