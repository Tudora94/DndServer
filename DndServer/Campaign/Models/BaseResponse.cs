namespace DndServer.Campaign.Models
{
    public class BaseResponse
    {
        private bool _success;
        private string _message = "";
        public bool Success { get => _success; set => _success = value; }
        public string Message { get => _message; set => _message = value; }
}
}
