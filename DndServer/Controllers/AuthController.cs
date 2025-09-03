using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using DndServer.User.Models;
using DndServer.User.Services;
using DndServer.Dal;
using DndServer.Campaign.Models;
using Microsoft.AspNetCore.Authorization;

namespace DndServer.Controllers
{


    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase

    {
        ClaimValidator claimValidator = new ClaimValidator();
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
                token.Message = "Invalid Login Credentials";
                return Ok(token);
            }
            if (!passwordHashing.VerifyPasswordHash(login.Password, user.PasswordHash, user.PaswordSalt))
            {
                token.Message = "Invalid Login Credentials";
                return Ok(token);
            }

            var privateKey = _configuration.GetSection("AppSettings:Token").Value;
            string tokenString = tokenGenerator.CreateAccessToken(user, privateKey);
            token.Token = tokenString;
            token.Message = "Login successful";
            token.Success = true;
            token.User = user.Id;

            return Ok(token);
        }

        [HttpGet("validateToken/{userId}")]
        [Authorize]

        public async Task<ActionResult<BaseResponse>> validateToken([System.Web.Http.FromUri] int userId)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authentication);

            var response = new BaseResponse();

            if (claimAccepted)
            {
                response.Success = true;
                response.Message = "token validated";
                return Ok(response);
            }
            else
            {
                response.Success = false;
                response.Message = "token invalid";
                return BadRequest(response);

            }
        }
    }
}
