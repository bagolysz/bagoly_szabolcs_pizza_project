using PizzaManagerEmployee.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.data
{
    interface IInvoiceWriter
    {
        void write(Order order);
    }
}
