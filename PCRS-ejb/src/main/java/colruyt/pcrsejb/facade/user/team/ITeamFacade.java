package colruyt.pcrsejb.facade.user.team;

import java.util.List;

import javax.ejb.Local;

import colruyt.pcrsejb.bo.user.UserBo;
import colruyt.pcrsejb.bo.user.team.EnrolmentBo;
import colruyt.pcrsejb.bo.user.team.TeamBo;
import colruyt.pcrsejb.facade.IFacade;
import colruyt.pcrsejb.util.exceptions.MemberAlreadyHasATeamException;
import colruyt.pcrsejb.util.exceptions.UserIsNotMemberOfTeamException;
import colruyt.pcrsejb.util.exceptions.validations.ValidationException;

@Local
public interface ITeamFacade extends IFacade<TeamBo>  {
	
	
	UserBo getManagerForUser(UserBo user) throws  ValidationException;
	TeamBo getTeamForUser(UserBo user) throws ValidationException;
	List<TeamBo> getTeamsOfManager(UserBo manager);
	void deleteUserFromTeam(TeamBo manipulatedTeamBo, EnrolmentBo enrolment, UserBo user) throws ValidationException;
	EnrolmentBo addUserToTeam(TeamBo manipulatedTeamBo, UserBo user, String userPrivilege) throws ValidationException;
	List<UserBo> getUsersOfTeam(TeamBo team);

}
