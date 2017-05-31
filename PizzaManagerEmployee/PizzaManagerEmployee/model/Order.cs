using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.model
{
    public class Order
    {
        public string _id { get; set; }
        public string userId { get; set; }
        public string addressId { get; set; }
        public double total { get; set; }
        public DateTime datetime { get; set; }
        public int status { get; set; }

        public string statusText { get; set; }
        public Address address { get; set; }
        public List<ShoppingCartItem> cart { get; set; }

        public void setStatusText()
        {
            switch (status)
            {
                case 1:
                    statusText = "Waiting to process";
                    break;
                case 2:
                    statusText = "Preparing";
                    break;
                case 3:
                    statusText = "Delivering";
                    break;
                case 4:
                    statusText = "Finished";
                    break;
                default:
                    statusText = "Unknown";
                    break;
            }
        }
    }
}
