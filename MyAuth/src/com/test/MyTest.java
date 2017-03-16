package com.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class MyTest {

	
	private static final String username = "AD_username";
	private static final String password = "AD_password";
	private static final String hostname = "windows_server_hostname";
	private static final String domain = "AD_DOMAIN";
	private static final String url = "Auth_Server_URL";


	public static void main(String args[]) throws ClientProtocolException, IOException{
		
		Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder
				.<AuthSchemeProvider>create()
		        .register(AuthSchemes.NTLM, new JCIFSNTLMSchemeFactory())
		        .build();
		

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
        		new NTCredentials(username, password, hostname, domain));
        
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setDefaultAuthSchemeRegistry(authSchemeRegistry)
		        .setDefaultCredentialsProvider(credentialsProvider)
		        .build();
		
        HttpGet httpget = new HttpGet(url); 
        HttpClientContext context = HttpClientContext.create();    

	    HttpResponse response = httpClient.execute(httpget, context);
	    System.out.println(response.getStatusLine().getStatusCode());
		
	}
}
