using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.domain
{
    interface IMainView
    {
        void ShowMessage(string msg);

        void PopulateDataOrders(List<model.Order> orders);

        void UpdateOrderCounter(int counter);
    }
}
