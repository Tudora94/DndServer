using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;

namespace DndServer.Dal
{
    public class AuthSql
    {
        private SqlConnection conn = new SqlConnection();
        ConnectionsSql connections = new ConnectionsSql();

        public bool AddUser(UserModel user)
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
                connections.SQLCloseConnection(conn);

                return true;
            }
            catch
            {
                return false;
            }


        }

        public bool AddEmail(UserModel user, string email)
        {
            try
            {
                connections.SqlOpenConnection(conn);

                string addUserEmail = @"INSERT INTO DndDb.dbo.UserEmail VALUES ((SELECT TOP(1) Id FROM DndDb.dbo.Users WHERE username = @username), @email)";
                SqlCommand cmdEmail = new SqlCommand(addUserEmail, conn);

                cmdEmail.Parameters.Add("@username", SqlDbType.VarChar).Value = user.UserName;
                cmdEmail.Parameters.Add("@email", SqlDbType.VarChar).Value = email;

                cmdEmail.ExecuteNonQuery();

                connections.SQLCloseConnection(conn);
                return true;
            }
            catch
            {
                return false;
            }
        }

        public bool CheckEmail(string email)
        {
            connections.SqlOpenConnection(conn);
            string checkEmail = @"IF EXISTS(SELECT * FROM DndDb.dbo.UserEmail WHERE Email = @email) SELECT 1";

            SqlCommand cmd = new SqlCommand(checkEmail, conn);

            cmd.Parameters.Add("@email", SqlDbType.VarChar).Value=email;
            SqlDataReader reader = cmd.ExecuteReader();

            if (reader.Read())
            {
                connections.SQLCloseConnection(conn);
                return false;
            }
            else
            {
                connections.SQLCloseConnection(conn);
                return true;
            }
        }

        public bool CheckUser(string user)
        {
            connections.SqlOpenConnection(conn);
            string checkEmail = @"IF EXISTS(SELECT * FROM DndDb.dbo.Users WHERE username = @user) SELECT 1";

            SqlCommand cmd = new SqlCommand(checkEmail, conn);

            cmd.Parameters.Add("@user", SqlDbType.VarChar).Value = user;
            SqlDataReader reader = cmd.ExecuteReader();

            if (reader.Read())
            {
                connections.SQLCloseConnection(conn);
                return false;
            }
            else
            {
                connections.SQLCloseConnection(conn);
                return true;
            }
        }

        public void LoginUser(string username, UserModel user)
        {
            DataTable dt = new DataTable();
            connections.SqlOpenConnection(conn);
            String login = @"SELECT username, Hash, Salt FROM DndDb.dbo.Users WHERE username = @user";

            SqlCommand cmd = new SqlCommand(login, conn);

            cmd.Parameters.Add("@user", SqlDbType.VarChar).Value = username;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            da.Fill(dt);

            connections.SQLCloseConnection(conn);
            foreach(DataRow dr in dt.Rows)
            {
                user.UserName = dr[0].ToString();
                user.PasswordHash = (byte[])dr[1];
                user.PaswordSalt = (byte[])dr[2];
            }
        }
    }
}
