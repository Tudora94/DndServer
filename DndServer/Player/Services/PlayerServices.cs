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
using System.Data;
using System;
using DndServer.Character;

namespace DndServer.Player.Services
{
    public class PlayerServices
    {
        PlayerSql sql = new PlayerSql();

        public bool ValidateRoomCode(string roomCode)
        {
            return sql.ValidateRoomCode(roomCode);
        }
        public int AddPlayer(NewCharacterModel model)
        {
            return sql.AddPlayer(model);
        }

        public (int campaignId, string campaignName) AddPlayerToCampaign(PlayerToCampaignRequest model)
        {
            return sql.AddPlayerToCampaign(model);
        }

        public GetPlayersResponse GetPlayers(int userId)
        {
            var response = new GetPlayersResponse();
            response.Players = new List<PlayerModel>();

            //sql to return dataTable
            var data = sql.GetPlayers(userId);

            if (data is DataTable dataTable)
            {
                foreach(DataRow dataRow in dataTable.Rows)
                {
                    var player = new PlayerModel
                    {
                        Id = Convert.ToInt32(dataRow["ID"]),
                        Name = dataRow["CharacterName"].ToString() ?? "",
                        UpdateTime = Convert.ToInt64(dataRow["UpdateTime"]),
                    };

                    if (dataRow["CampaignId"] is int campaignId)
                    {
                        player.CampaignId = campaignId;
                        player.CampaignName = dataRow["CampaignName"].ToString() ?? "";
                    }

                    response.Players.Add(player);
                }
                response.Success = true;
            }
            return response;
        }

        public bool DeletePlayer(int characterId, int userId)
        {
            return sql.DeletePlayer(characterId, userId);
        }

        public bool UpdatePlayer(UpdateCharacterRequest request)
        {
            return sql.UpdatePlayer(request);
        }

    }

}
