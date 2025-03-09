using DndServer.User.Models;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace DndServer.User.Services
{
    public class TokenGenerator
    {
        public string CreateToken(UserModel user, string privateKey)
        {

            List<Claim> claims = new List<Claim>
            {
                new Claim("name", user.UserName),
                new Claim("GeneratedTime", DateTime.Now.ToString("HH:mm:ss"))
            };

            var key = new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(privateKey));

            var cred = new SigningCredentials(key, SecurityAlgorithms.HmacSha512Signature);

            var token = new JwtSecurityToken(claims: claims, expires: DateTime.Now.AddDays(1), signingCredentials: cred);

            var jwt = new JwtSecurityTokenHandler().WriteToken(token);

            return jwt;
        }
    }
}
