package org.lolobored.plex.apis;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lolobored.http.HttpUtil;
import org.lolobored.http.HttpException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class PlexApis {

    private static final String plexUrl = "https://plex.tv/users/sign_in.json";

    public static void authenticate(String userName, String password) throws HttpException {

        Map<String, String> httpHeader = new HashMap();
        httpHeader.put("Content-Type", HttpUtil.FORM_URLENCODED);
        httpHeader.put("X-Plex-Client-Identifier", "APP1");
        httpHeader.put("charset", "utf-8");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user[login]", userName));
        nameValuePairs.add(new BasicNameValuePair("user[password]", password));
        String response = HttpUtil.getInstance(true).postUrlEncoded(plexUrl, nameValuePairs, httpHeader);
        System.out.println(response);
    }

    
}


