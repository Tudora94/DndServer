using DndServer.Dal;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace DndServer.User.Services
{
    public class ClaimValidator
    {
        public bool validateClaimUser(int userId, string token, AuthSql auth)
        {
            try
            {
                var handler = new JwtSecurityTokenHandler();
                var jwtToken = handler.ReadJwtToken(token);
                var nameClaim = jwtToken.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Name)?.Value;

                var givenUser = auth.getUserNameFromId(userId);
                if (nameClaim == givenUser)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch
            {
                return false;
            }
        }
    }
}
