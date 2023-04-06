using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;

namespace DndServer.Dal
{
    public class AuthSql
    {
        private SqlConnection conn = new SqlConnection();
        ConnectionsSql connections = new ConnectionsSql();

        public bool AddUser(UserModel user, string email)
        {
            try
            {
                connections.SqlOpenConnection(conn);

                string createUser = @"INSERT INTO DndDb.dbo.Users VALUES (@username, @PasswordHash, @PasswordSalt)";
                SqlCommand cmd = new SqlCommand(createUser, conn);

                cmd.Parameters.Add("@username", SqlDbType.VarChar).Value = user.UserName;
                cmd.Parameters.Add("@PasswordHash", SqlDbType.VarBinary).Value = user.PasswordHash;
                cmd.Parameters.Add("@PasswordSalt", SqlDbType.VarBinary).Value = user.PaswordSalt;

                cmd.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }


        }

    }
}
