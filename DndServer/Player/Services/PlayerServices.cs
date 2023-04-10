using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;
using DndServer.Campaign.Services;
using System.Net;
using System.Diagnostics;
using DndServer.Player.Models;
using DndServer.Player.Services;

namespace DndServer.Player.Services
{
    public class PlayerServices
    {
        PlayerSql sql = new PlayerSql();

        public bool ValidateRoomCode(string roomCode)
        {
            return sql.ValidateRoomCode(roomCode);
        }
        public string AddPlayer(NewCharacterModel model)
        {
            if (sql.AddPlayer(model))
            {
                return "Player Added";
            }
            else
            {
                return "Update Failed";
            }
        }

    }

}
