using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;
using DndServer.Campaign.Models;
using DndServer.Player.Models;


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
        public bool AddPlayer(NewCharacterModel model)
        {
            try
            {
                SqlConnection conn = new SqlConnection();
                connections.SqlOpenConnection(conn);

                string query = @"DndDb.dbo.AddNewPlayer";
                SqlCommand command = new SqlCommand(query, conn);
                command.CommandType = CommandType.StoredProcedure;

                command.Parameters.Add("@roomCode", SqlDbType.VarChar).Value = model.RoomCode;
                command.Parameters.Add("@name", SqlDbType.VarChar).Value = model.Name;
                command.Parameters.Add("@username", SqlDbType.VarChar).Value = model.UserName;

                command.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }


        }

    }
}
