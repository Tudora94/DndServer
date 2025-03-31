namespace DndServer.Player.Models
{
    public class CreatePlayerResponse
    {
        private bool _success = false;
        private int _playerId = 0;

        public bool Success { get => _success; set => _success = value; }
        public int PlayerId { get => _playerId; set => _playerId = value; }
    }
}
