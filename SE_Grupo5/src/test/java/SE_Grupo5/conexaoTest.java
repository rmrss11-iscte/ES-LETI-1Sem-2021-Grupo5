package SE_Grupo5;

import java.io.IOException;

import org.junit.Test;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.*;
import com.julienvey.trello.impl.http.ApacheHttpClient;

import junit.framework.TestCase;

public class conexaoTest extends TestCase {
	
	@Test
	public void testConexaoTrello() {
        Trello trello = Conexao.conexaoTrello("95535b17caae83c1c1435cbe99dbcf24","195e391a7ce8c837658de6d6473ac882450d8115dbe75d641f8c1cf6a396fd97");
        Trello trello2 = new TrelloImpl("95535b17caae83c1c1435cbe99dbcf24","195e391a7ce8c837658de6d6473ac882450d8115dbe75d641f8c1cf6a396fd97", new ApacheHttpClient());
        String boardId = "61675accbcf8367649df3b80";
        String org = trello.getBoardOrganization(boardId).getId();
        String org2 = trello2.getBoardOrganization(boardId).getId();
        assertEquals(org,org2);
    }

    @Test
    public void testConexaoGitHub() {
    	
    	String GitHubToken = "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";
    	try {
			GitHub gitHub = Conexao.conexaoGitHub(GitHubToken);
			GitHub gitHubApi = new GitHubBuilder().withOAuthToken(GitHubToken).build();
			String url = gitHub.getApiUrl();
			String url2 = gitHubApi.getApiUrl();
			assertSame(url2,url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
	
}
