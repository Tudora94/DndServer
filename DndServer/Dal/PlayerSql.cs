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
                int campaignId = Convert.ToInt32(command.Parameters["@playerId"].Value);

                connections.SQLCloseConnection(conn);
                return campaignId;
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

                string sqlString = @"SELECT ID, UserId, CampaignId, CharacterName, UpdateTime FROM DndDb.dbo.PlayerCharacterName WHERE UserId = @userId";
                SqlCommand command = new SqlCommand(sqlString, conn);

                command.Parameters.Add("userId", SqlDbType.Int).Value=UserId;

                DataTable dt = new DataTable();

                SqlDataAdapter da = new SqlDataAdapter(command);
                da.Fill(dt);
                connections.SQLCloseConnection(conn);
                return dt;
            }
            catch 
            {
                return false;
            }
        }

    }
}
