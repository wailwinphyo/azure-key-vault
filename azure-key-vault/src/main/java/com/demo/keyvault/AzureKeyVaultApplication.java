package com.demo.keyvault;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretBundle;

@SpringBootApplication
public class AzureKeyVaultApplication {

	private static final String VAULT_URI = "https://idtechvault.vault.azure.net";

	public static void main(String[] args) {
		
		KeyVaultClient client = ClientSecretKeyVaultCredential.getAuthenticatedClient();
		SecretBundle returnedKeyBundle = client.getSecret(VAULT_URI, "bdk");
		System.out.println(returnedKeyBundle.toString());
		
		/*
		 * KeyVaultClient client = new KeyVaultClient(new
		 * AzureKeyVaultCredential("a0760de1-38a0-402e-b6b2-3f4b90d1c277",
		 * "IK7@jTY3:DDOrk5@5AHCaB=:65+pHrAM")); SecretBundle returnedKeyBundle =
		 * client.getSecret(VAULT_URI, "bdk");
		 * System.out.println(returnedKeyBundle.value());
		 */
		
		//SpringApplication.run(AzureKeyVaultApplication.class, args);
	}

}
