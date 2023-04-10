using DndServer.Campaign.Models;
using DndServer.Dal;
using System.Data;

namespace DndServer.Campaign.Services
{
    public class CampaignGeneratorService
    {
        public CampaignModel Campaign(int campaignId)
        {
            CampaignSql campaignSql = new CampaignSql();
            CampaignModel campaignModel = new CampaignModel();


            DataSet CampaignDataSet = campaignSql.GetCampaign(campaignId);
            DataTable CampaignAttributes = CampaignDataSet.Tables[0];

            campaignModel.CampaignID = campaignId;

            campaignModel.CampaignName = Convert.ToString(CampaignAttributes.Rows[0][1]);
            campaignModel.AdvancementType =  Convert.ToBoolean(CampaignAttributes.Rows[0][2]);
            campaignModel.HpType = Convert.ToBoolean(CampaignAttributes.Rows[0][3]);
            campaignModel.WeightType = Convert.ToBoolean(CampaignAttributes.Rows[0][4]);
            campaignModel.GoldWeight = Convert.ToBoolean(CampaignAttributes.Rows[0][5]);

            DataTable CampaignSources = CampaignDataSet.Tables[1];

            campaignModel.Sources.PHB_5TH_EDITION_CONTENT1 = Convert.ToBoolean(CampaignSources.Rows[0][0]);
            campaignModel.Sources.HOMEBREW_CONTENT1 = Convert.ToBoolean(CampaignSources.Rows[0][1]);
            campaignModel.Sources.ONLINE_CONTENT1 = Convert.ToBoolean(CampaignSources.Rows[0][2]);
            campaignModel.Sources.OTHER_SOURCE_CONTENT1 = Convert.ToBoolean(CampaignSources.Rows[0][3]);

            return campaignModel;

        }
    }
}
