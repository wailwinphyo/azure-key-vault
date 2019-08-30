package com.demo.keyvault;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.authentication.KeyVaultCredentials;
import com.microsoft.rest.credentials.ServiceClientCredentials;

public class ClientSecretKeyVaultCredential {

	public static KeyVaultClient getAuthenticatedClient() {
		return new KeyVaultClient(createCredential());
	}

	private static ServiceClientCredentials createCredential() {
		return new KeyVaultCredentials() {

			@Override
			public String doAuthenticate(String authorization, String resource, String scope) {
				AuthenticationResult result = getAccessTokenFromClientCredentials(authorization, resource);
				return result.getAccessToken();
			}
		};
	}

	private static AuthenticationResult getAccessTokenFromClientCredentials(String authorization, String resource) {
		String clientId = "a0760de1-38a0-402e-b6b2-3f4b90d1c277"; 	// System.getProperty("AZURE_CLIENT_ID");
		String clientKey = "IK7@jTY3:DDOrk5@5AHCaB=:65+pHrAM"; 	// System.getProperty("AZURE_CLIENT_SECRET");
		
		AuthenticationResult result = null;
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(1);
			AuthenticationContext context = new AuthenticationContext(authorization, false, service);
			ClientCredential credentials = new ClientCredential(clientId, clientKey);
			Future<AuthenticationResult> future = context.acquireToken(resource, credentials, null);
			result = future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			service.shutdown();
		}

		if (result == null) {
			throw new RuntimeException("authentication result was null");
		}
		return result;
	}
}
