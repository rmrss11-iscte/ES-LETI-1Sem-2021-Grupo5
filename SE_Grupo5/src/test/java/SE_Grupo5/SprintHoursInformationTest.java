package SE_Grupo5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SprintHoursInformationTest {

	@Test
	public void testMemberHoursInformation() {
		MemberHoursInformation member = new MemberHoursInformation("user");
		assertSame("user",member.getUser());
		assertEquals(0,member.getSpentTime(),0.1);
		assertEquals(0,member.getEstimateTime(),0.1);
		member.addTime(10, 20);
		assertEquals(10,member.getSpentTime(),0.1);
		assertEquals(20,member.getEstimateTime(),0.1);
		assertEquals("user has estimated 20.0hours and spent 10.0hours!",member.getInfomation());
	}
	
	@Test
	public void testSprintHoursInformation() {
		SprintHoursInformation sprint = new SprintHoursInformation("sprint 1");
		assertEquals("sprint 1", sprint.getSprint());
		MemberHoursInformation member = new MemberHoursInformation("user");
		MemberHoursInformation member2 = new MemberHoursInformation("user2");
		sprint.getMemberHoursInformationList().add(member);
		sprint.getMemberHoursInformationList().add(member2);
		assertEquals(0,sprint.getEstimateTime(),0.1);
		assertEquals(0,sprint.getSpentTime(),0.1);
		
		List<MemberHoursInformation> list = new ArrayList<MemberHoursInformation>();
		list.add(member);
		list.add(member2);
		assertEquals(list,sprint.getMemberHoursInformationList());
		
		assertEquals(false,sprint.hasSpentTime());
		
		MemberHoursInformation member3 = new MemberHoursInformation("user3");
		member3.addTime(10, 10);
		sprint.getMemberHoursInformationList().add(member3);
		
		assertEquals(true,sprint.hasSpentTime());
		
		String texto = "On board sprint 1:\nuser has estimated 0.0hours and spent 0.0hours!\nuser2 has estimated 0.0hours and spent 0.0hours!\nuser3 has estimated 10.0hours and spent 10.0hours!";
		assertEquals(texto,sprint.getInfomation());
	}

}
