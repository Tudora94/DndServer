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

            RegistrationResponseModel responseModel = new RegistrationResponseModel();

            if (!authentication.CheckUser(user.UserName))
            {
                responseModel.success = false;
                responseModel.message = "UserName already in use";
                return Ok(responseModel);
            }
            if (!authentication.CheckEmail(email))
            {
                responseModel.success = false;
                responseModel.message = "Email already in use";
                return Ok(responseModel);
            }

            if (authentication.AddUser(user) && authentication.AddEmail(user, email))
            {
                responseModel.success = true;
                responseModel.message = "User Registered Successfully";
                return Ok(responseModel);
            }
            else
            {
                responseModel.success = false;
                responseModel.message = "User was not added";
                return BadRequest(responseModel);
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
