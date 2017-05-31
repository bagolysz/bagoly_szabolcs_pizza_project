using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.model
{
    public class Product
    {
        public string _id { get; set; }
        public string title { get; set; }
        public string description { get; set; }
        public double price { get; set; }
        public int productCategory { get; set; }
    }
}
