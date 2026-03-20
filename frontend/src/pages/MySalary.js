import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Banknote, TrendingUp, AlertCircle, FileText } from 'lucide-react';

const MySalary = () => {
  const [salary, setSalary] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSalary();
  }, []);

  const fetchSalary = async () => {
    try {
      const res = await api.get('/employee/salary');
      setSalary(res.data);
    } catch (err) {
      setSalary(null);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-900">Payroll Details</h1>
        <p className="text-slate-500">View your current salary and payment notes.</p>
      </div>

      {salary ? (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="md:col-span-1 bg-slate-900 text-white p-8 rounded-[2.5rem] shadow-2xl relative overflow-hidden group">
            <div className="absolute -top-10 -right-10 w-40 h-40 bg-blue-500/20 rounded-full blur-3xl group-hover:bg-blue-500/30 transition-colors"></div>
            <Banknote className="text-blue-400 mb-6" size={40} />
            <p className="text-slate-400 font-medium mb-1">Monthly Salary</p>
            <h2 className="text-4xl font-extrabold tracking-tight">${salary.amount.toLocaleString()}</h2>
            <div className="mt-8 flex items-center gap-2 text-green-400 text-sm font-bold">
               <TrendingUp size={16} />
               <span>Up to date</span>
            </div>
          </div>

          <div className="md:col-span-2 space-y-6">
             <div className="bg-white p-8 rounded-[2.5rem] shadow-sm border border-slate-200">
                <h3 className="text-lg font-bold mb-4 flex items-center gap-2 text-slate-800">
                    <FileText size={20} className="text-blue-500" />
                    <span>Payment Notes from HR</span>
                </h3>
                <p className="text-slate-600 leading-relaxed bg-slate-50 p-6 rounded-2xl italic">
                   {salary.notes || "No additional notes provided by HR for this cycle."}
                </p>
             </div>

             <div className="bg-blue-50 p-6 rounded-[2rem] border border-blue-100 flex items-start gap-4">
                <div className="p-3 bg-blue-100 text-blue-600 rounded-xl">
                    <AlertCircle size={20} />
                </div>
                <div>
                   <h4 className="font-bold text-blue-900">Security Note</h4>
                   <p className="text-sm text-blue-700/80">Only you and authorized HR personnel can view these details. Contact HR if you find any discrepancies.</p>
                </div>
             </div>
          </div>
        </div>
      ) : (
        <div className="bg-white p-12 rounded-[2.5rem] border border-dashed border-slate-300 text-center">
             <p className="text-slate-400 font-medium">Salary details not yet updated by HR.</p>
        </div>
      )}
    </div>
  );
};

export default MySalary;
