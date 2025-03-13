namespace DndServer.User.Models
{
    public class TokenModel
    {
        private bool _success = false;
        private string _message = "";
        private string? _token;
        private string? _refreshToken;
        private int? _user;

        public bool Success { get => _success; set => _success = value; }
        public string? Token { get => _token; set => _token = value; }
        public string Message { get => _message; set => _message = value; }
        public string? RefreshToken { get => _refreshToken; set => _refreshToken = value; }
        public int? User { get => _user; set => _user = value; }

    }
}
