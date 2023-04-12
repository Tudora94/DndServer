using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using DndServer.User.Models;
using DndServer.User.Services;
using DndServer.Dal;

namespace DndServer.Controllers
{


    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase

    {

        private readonly IConfiguration _configuration;

        public AuthController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        AuthSql authentication = new AuthSql();
        PasswordHashing passwordHashing = new PasswordHashing();


        [HttpPost("Register")]
        public async Task<ActionResult<string>> Register(RegistrationModel request)
        {
            UserModel user = new UserModel();

            passwordHashing.CreatePasswordHash(request.password, out byte[] passwordHash, out byte[] passwordSalt);

            user.firstName = request.firstName;
            user.UserName = request.username;
            user.PasswordHash = passwordHash;
            user.PaswordSalt = passwordSalt;
            string email = request.email;

            if (!authentication.CheckUser(user.UserName))
            {
                return BadRequest("UserName already in use");
            }
            if (!authentication.CheckEmail(email))
            {
                return BadRequest("Email already in use");
            }

            if (authentication.AddUser(user) && authentication.AddEmail(user, email))
            {
                return Ok("user Registered Successfully");
            }
            else
            {
                return BadRequest("user Was not added");
            }


        }

        [HttpPost("Login")]
        public async Task<ActionResult<TokenModel>> Login(UserLoginModel login) //return TokenModel
        {
            TokenModel token = new TokenModel();
            TokenGenerator tokenGenerator = new TokenGenerator();
            UserModel user = new UserModel();
            authentication.LoginUser(login.UserName, user);

            if (user.UserName == "")
            {
                return BadRequest("Invalid Login Credentials");
            }
            if (!passwordHashing.VerifyPasswordHash(login.Password, user.PasswordHash, user.PaswordSalt))
            {
                return BadRequest("Invalid Login Credentials");
            }

            var privateKey = _configuration.GetSection("AppSettings:Token").Value;
            string tokenString = tokenGenerator.CreateToken(user, privateKey);
            token.Token = tokenString;
            return Ok(token);
        }
    }
}
