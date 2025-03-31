using System.Security.Cryptography;

namespace DndServer.Player.Models
{
    public class GetPlayersResponse
    {
        private bool _success = false;
        private List<PlayerModel>? _players;
        public bool Success { get => _success; set => _success = value; }
        public List<PlayerModel>? Players { get => _players; set => _players = value; }

}
}
