namespace DndServer.User.Models
{
    public class UserModel
    {
        public string UserName { get; set; } = string.Empty;
        public byte[] PasswordHash { get; set; }
        public byte[] PaswordSalt { get; set; }
    }
}
