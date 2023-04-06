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

        AuthSql authentication = new AuthSql();

        [HttpPost("Register")]
        public async Task<ActionResult<string>> Register(RegistrationModel request)
        {
            PasswordHashing passwordHashing = new PasswordHashing();
            UserModel user = new UserModel();

            passwordHashing.CreatePasswordHash(request.password, out byte[] passwordHash, out byte[] passwordSalt);

            user.UserName = request.username;
            user.PasswordHash = passwordHash;
            user.PaswordSalt = passwordSalt;
            string email = request.email;

            bool userAdded = authentication.AddUser(user, email);

            if (userAdded)
            {
                return Ok("user Registered Successfully");
            }
            else
            {
                return BadRequest("user Was not added");
            }


        }
    }
}
