using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;
using DndServer.Campaign.Models;
using DndServer.Player.Models;
using System.Reflection;


namespace DndServer.Dal
{
    public class PlayerSql
    {
        ConnectionsSql connections = new ConnectionsSql();

        public bool ValidateRoomCode(string roomCode)
        {
            SqlConnection conn = new SqlConnection();
            connections.SqlOpenConnection(conn);

            string Query = @"IF EXISTS(SELECT * FROM DndDb.dbo.CampaignRoomCode WHERE CampaignCode = @code AND ExpiryDateTime > @time) SELECT 1";

            SqlCommand cmd = new SqlCommand(Query, conn);
            string currentTimeSql = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");

            cmd.Parameters.Add("@code", SqlDbType.VarChar).Value = roomCode;
            cmd.Parameters.Add("@time", SqlDbType.DateTime).Value = currentTimeSql;
            SqlDataReader reader = cmd.ExecuteReader();

            if (reader.Read())
            {
                connections.SQLCloseConnection(conn);
                return true;
            }
            else
            {
                connections.SQLCloseConnection(conn);
                return false;
            }
        }

        public (int campaignId, string campaignName) AddPlayerToCampaign(PlayerToCampaignRequest request)
        {
            SqlConnection conn = new SqlConnection();
            try
            {
                connections.SqlOpenConnection(conn);

                string query = @"DndDb.dbo.AddPlayerToCampaign";
                SqlCommand command = new SqlCommand(query, conn);
                command.CommandType = CommandType.StoredProcedure;

                command.Parameters.Add("@userId", SqlDbType.Int).Value = request.UserId;
                command.Parameters.Add("@updateTime", SqlDbType.BigInt).Value = request.UpdateTime;
                command.Parameters.Add("@characterId", SqlDbType.Int).Value = request.CharacterId;
                command.Parameters.Add("@roomCode", SqlDbType.VarChar).Value = request.RoomCode;

                command.Parameters.Add("@campaignId", SqlDbType.Int);
                command.Parameters["@campaignId"].Direction = ParameterDirection.Output;
                command.Parameters.Add("@campaignName", SqlDbType.VarChar, 255);
                command.Parameters["@campaignName"].Direction = ParameterDirection.Output;


                command.ExecuteNonQuery();
                int campaignId = Convert.ToInt32(command.Parameters["@campaignId"].Value);
                string campaignName = command.Parameters["@campaignName"].Value.ToString()??"";

                connections.SQLCloseConnection(conn);
                return (campaignId, campaignName);

            }
            catch (Exception ex)
            {
                return (0, string.Empty);
            }

        }
        public int AddPlayer(NewCharacterModel model)
        {
            SqlConnection conn = new SqlConnection();

            try
            {
                connections.SqlOpenConnection(conn);

                string query = @"DndDb.dbo.AddNewPlayer";
                SqlCommand command = new SqlCommand(query, conn);
                command.CommandType = CommandType.StoredProcedure;

                command.Parameters.Add("@name", SqlDbType.VarChar).Value = model.Name;
                command.Parameters.Add("@userId", SqlDbType.Int).Value = model.UserId;
                command.Parameters.Add("@updateTime", SqlDbType.BigInt).Value = model.UpdateTime;

                command.Parameters.Add("@playerId", SqlDbType.Int);
                command.Parameters["@playerId"].Direction = ParameterDirection.Output;

                int i = command.ExecuteNonQuery();
                int playerId = Convert.ToInt32(command.Parameters["@playerId"].Value);

                connections.SQLCloseConnection(conn);
                return playerId;
            }
            catch
            {
                connections.SQLCloseConnection(conn);
                return 0;
            }


        }

        public object GetPlayers(int UserId)
        {
            SqlConnection conn = new SqlConnection();

            try
            {
                connections.SqlOpenConnection(conn);

                string sqlString = @"SELECT PCN.ID, PCN.UserId, CampaignId, CharacterName, PCN.UpdateTime, CampaignName FROM DndDb.dbo.PlayerCharacterName AS PCN JOIN DndDb.dbo.CampaignName AS CN ON PCN.CampaignId = CN.Id WHERE PCN.UserId = @userId";
                SqlCommand command = new SqlCommand(sqlString, conn);

                command.Parameters.Add("userId", SqlDbType.Int).Value=UserId;

                DataTable dt = new DataTable();

                SqlDataAdapter da = new SqlDataAdapter(command);
                da.Fill(dt);
                connections.SQLCloseConnection(conn);
                return dt;
            }
            catch (Exception ex)
            {
                string msg = ex.Message;
                return false;
            }
        }

        public bool DeletePlayer(int characterId, int userId)
        {
            SqlConnection conn = new SqlConnection();
            try
            {
                connections.SqlOpenConnection( conn );
                string sqlString = @"DELETE FROM DndDb.dbo.PlayerCharacterName WHERE UserId = @userId AND ID = @charId";
                SqlCommand command = new SqlCommand( sqlString, conn);

                command.Parameters.Add("userId", SqlDbType.Int).Value=userId;
                command.Parameters.Add("charId", SqlDbType.Int).Value = characterId;

                command.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
        }

        public bool UpdatePlayer(UpdateCharacterRequest request)
        {
            SqlConnection conn = new SqlConnection();
            try
            {
                connections.SqlOpenConnection( conn );
                string sqlString = @"UPDATE DndDb.dbo.PlayerCharacterName SET CharacterName = @charName, UpdateTime = @updateTime WHERE ID = @playerId AND UserId = @userId";
                SqlCommand command = new SqlCommand(sqlString, conn);

                command.Parameters.Add("charName", SqlDbType.VarChar).Value = request.Name;
                command.Parameters.Add("updateTime", SqlDbType.BigInt).Value = request.UpdateTime;
                command.Parameters.Add("playerId", SqlDbType.Int).Value = request.Id;
                command.Parameters.Add("userId", SqlDbType.Int).Value = request.UserId;

                command.ExecuteNonQuery();
                return true;
            } catch
            {
                return false;
            }
        }

    }
}
