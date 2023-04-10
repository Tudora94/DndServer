using System;
using System.Data.SqlClient;
using System.Data;
using System.Data.SqlTypes;

namespace DndServer.Dal
{
    public class SqlDal
    {
        private SqlConnection conn = new SqlConnection();
        private bool ExistanceCheck = false;

        public void SqlOpenConnection()
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
            if (!ExistanceCheck)
            {
                createDataBase();
            }


        }
        public void SQLCloseConnection()
        {
            conn.Close();
        }
        public int getCampaignId(string CampaignName)
        {
            DataTable dt = new DataTable();

            String Insertquery = String.Format("Insert Into DndDb.dbo.Campaign (CampaignName) VALUES ('{0}')", CampaignName);

            SqlOpenConnection();

            SqlCommand insert = new SqlCommand(Insertquery, conn);
            insert.ExecuteNonQuery();

            String query = String.Format("SELECT CampaignID FROM DndDb.dbo.Campaign WHERE CampaignName = '{0}'",CampaignName);

            SqlCommand cmd = new SqlCommand(query, conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            da.Fill(dt);

            SQLCloseConnection();

            int CampaignId = 0;

            foreach(DataRow dr in dt.Rows)
            {
                CampaignId = Convert.ToInt32(dr[0]);
            }

            return CampaignId;
        }

        public void createDataBase()
        {
            ExistanceCheck = true;
            bool dbExists = false;

            DataTable dt = new DataTable();

            String query = "IF DB_ID('DndDb') IS NOT NULL SELECT 'True'";
            SqlCommand cmd = new SqlCommand(query, conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            da.Fill(dt);

            try
            {
                string Exists = Convert.ToString(dt.Rows[0]);
                dbExists = true;
            }
            catch
            {
                dbExists = false;
            }

            if (!dbExists)
            {
                string str = "CREATE DATABASE DndDb";
                SqlCommand createDb = new SqlCommand(str, conn);

                createDb.ExecuteNonQuery();

                string CampaignTable = "CREATE TABLE DndDb.dbo.Campaign (" +
                    "CampaignId INT IDENTITY(1,1) PRIMARY KEY," +
                    "CampaignName VARCHAR(255))";
                SqlCommand createTable = new SqlCommand(CampaignTable, conn);

                createTable.ExecuteNonQuery();
            }

        }
    }
}
