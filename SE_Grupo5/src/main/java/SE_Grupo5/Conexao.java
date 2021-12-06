package SE_Grupo5;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
/**
 * Esta classe server para fazer as conexões ao Trello e ao GitHub 
 *
 */
public class Conexao {	
	
	/**
	 * Este método serve para fazer a conexão ao Trello
	 * 
	 * @param trelloKey Representa a key do utilizador no trello
	 * @param trelloToken Representa o token do utilizador no trello
	 * @return trelloApi Representa a conexão ao Trello
	 */
	public static Trello conexaoTrello(String trelloKey, String trelloToken) {
		Trello trelloApi = new TrelloImpl(trelloKey,trelloToken, new ApacheHttpClient()); 
		return trelloApi;
	}
	
	/**
	 * Este método serve para fazer a conexão ao GitHub
	 * 
	 * @param GitHubToken Representa o token do utilizador no GitHub
	 * @return gitHubApi Representa a conexão ao GitHub
	 * @throws IOException
	 */
	public static GitHub conexaoGitHub(String GitHubToken) throws IOException {
		GitHub gitHubApi = new GitHubBuilder().withOAuthToken(GitHubToken).build();
		return gitHubApi;
	}
}
