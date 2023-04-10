using System.Data.SqlClient;

namespace DndServer.Dal
{
    public class ConnectionsSql
    {
        public void SqlOpenConnection(SqlConnection conn)
        {
            var datasource = Environment.MachineName;
            var database = "Master";
            var username = @"DnDLogin";
            var password = @"Natural1";

            string connString = @"Data Source=" + datasource + ";Initial Catalog=" + database + ";Persist Security Info=True;User ID=" + username + ";Password=" + password;

            conn.ConnectionString = connString;

            try
            {

                conn.Open();

            }
            catch (Exception e)
            {
                //Console.WriteLine("Error: " + e.Message);
            }
        }
        public void SQLCloseConnection(SqlConnection conn)
        {
            conn.Close();
        }
    }
}
