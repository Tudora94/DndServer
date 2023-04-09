using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;
using DndServer.Campaign.Models;



namespace DndServer.Dal

{
    public class CampaignSql
    {
        private SqlConnection conn = new SqlConnection();
        ConnectionsSql connections = new ConnectionsSql();

        public int CreateCampaign(CreateCampaignModel model)
        {
            try
            {
                connections.SqlOpenConnection(conn);

                string SetCampaignId = @"INSERT INTO DndDb.dbo.CampaignName VALUES ((SELECT Id FROM DndDb.dbo.Users WHERE username = @username), @CampaignName)";
                SqlCommand cmdSetCampaignId = new SqlCommand(SetCampaignId, conn);

                cmdSetCampaignId.Parameters.Add("@username", SqlDbType.VarChar).Value = model.UserName1;
                cmdSetCampaignId.Parameters.Add("@CampaignName", SqlDbType.VarChar).Value = model.Name;

                cmdSetCampaignId.ExecuteNonQuery();

                string getCampaignId = @"SELECT Id FROM DndDb.dbo.CampaignName WHERE UserId = (SELECT Id FROM DndDb.dbo.Users WHERE username = @username) AND CampaignName = @CampaignName";
                SqlCommand cmdGetCampaignId = new SqlCommand(getCampaignId, conn);

                cmdGetCampaignId.Parameters.Add("@username", SqlDbType.VarChar).Value = model.UserName1;
                cmdGetCampaignId.Parameters.Add("@CampaignName", SqlDbType.VarChar).Value = model.Name;

                DataTable dt = new DataTable();

                SqlDataAdapter da = new SqlDataAdapter(cmdGetCampaignId);
                da.Fill(dt);
                connections.SQLCloseConnection(conn);

                int CampaignId = 0;

                foreach(DataRow dr in dt.Rows)
                {
                    CampaignId = Convert.ToInt32(dr[0]);
                }

                return CampaignId;
            }
            catch
            {
                return 0;
            }
        }

        public CampaignListModel getCampaigns(string userName)
        {
            connections.SqlOpenConnection(conn);

            string sqlString = @"Select Id, CampaignName FROM DndDb.dbo.CampaignName WHERE UserId = (SELECT Id FROM DndDb.dbo.Users WHERE username = @username)";
            SqlCommand CmdGetCampaigns = new SqlCommand(sqlString, conn);

            CmdGetCampaigns.Parameters.Add("@username", SqlDbType.VarChar).Value=userName;

            DataTable dt = new DataTable();

            SqlDataAdapter da = new SqlDataAdapter(CmdGetCampaigns);
            da.Fill(dt);
            connections.SQLCloseConnection(conn);

            CampaignListModel campaignListModel = new CampaignListModel();

            foreach(DataRow dr in dt.Rows)
            {
                GetCampaignModel getCampaignModel = new GetCampaignModel();
                getCampaignModel.Id = Convert.ToInt32(dr[0]);
                getCampaignModel.CampaignName = dr[1].ToString();
                campaignListModel.CampaignModels.Add(getCampaignModel);
            }

            return campaignListModel;
        }

    }
}
