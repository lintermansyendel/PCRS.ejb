package colruyt.pcrsejb.service.bl.user;

import java.util.List;

import javax.ejb.Local;

import colruyt.pcrsejb.entity.user.User;
import colruyt.pcrsejb.entity.user.privilege.PrivilegeType;
import colruyt.pcrsejb.entity.user.privilege.TeamMemberUserPrivilege;
import colruyt.pcrsejb.entity.user.team.Enrolment;
import colruyt.pcrsejb.service.bl.IServiceBl;
import colruyt.pcrsejb.util.exceptions.NoExistingEmailException;
import colruyt.pcrsejb.util.exceptions.NoExistingMemberException;
import colruyt.pcrsejb.util.exceptions.validations.ValidationException;

@Local
public interface IUserServiceBl extends IServiceBl<User>   { 
	public Boolean hasPrivilege(User user, PrivilegeType privilegeType, Boolean active);  
	public User getUserByEmail(String email) throws NoExistingEmailException;  
	public List<User> getUsersByShortName(String shortName);
	public User getUserByEnrolment(Enrolment enrolment) throws NoExistingMemberException;
	public TeamMemberUserPrivilege getActiveTeamMemberPrivilege(User user);
	public User login(String email , String password) throws ValidationException;
}
