namespace DndServer.User.Models
{
    public class RegistrationModel
    {
        public string username { get; set; } = string.Empty;
        public string password { get; set; } = string.Empty;
        public string confirmPassword { get; set; } = string.Empty;
        public string email { get; set; } = string.Empty;

    }
}
