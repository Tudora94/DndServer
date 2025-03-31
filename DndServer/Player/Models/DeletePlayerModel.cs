namespace DndServer.Player.Models
{
    public class DeletePlayerModel
    {
        private int _playerId;
        private int _userId;

        public int PlayerId { get => _playerId; set => _playerId = value; }

        public int UserId { get => _userId; set => _userId = value; }
    }
}
