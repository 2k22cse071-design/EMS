import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Banknote, Save, DollarSign } from 'lucide-react';

const HRSalaryManagement = () => {
  const [employees, setEmployees] = useState([]);
  const [selectedEmp, setSelectedEmp] = useState(null);
  const [salaryData, setSalaryData] = useState({
    amount: '',
    notes: ''
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      const res = await api.get('/hr/employees');
      setEmployees(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await api.post('/hr/salary/set', {
        employeeId: selectedEmp.id,
        ...salaryData
      });
      alert('Salary updated successfully');
    } catch (err) {
      alert('Error updating salary');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-6xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-900">Salary Management</h1>
        <p className="text-slate-500">Manage employee payroll and bonuses.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="bg-white p-8 rounded-3xl shadow-sm border border-slate-200">
             <div className="flex items-center gap-3 mb-6 p-4 bg-orange-50 text-orange-700 rounded-2xl">
                 <Banknote size={24} />
                 <span className="font-bold">Select Employee to Update Salary</span>
             </div>
             <div className="space-y-4 max-h-[400px] overflow-y-auto pr-2">
                 {employees.map(emp => (
                     <div 
                         key={emp.id} 
                         onClick={() => setSelectedEmp(emp)}
                         className={`p-4 rounded-2xl border transition-all cursor-pointer ${
                             selectedEmp?.id === emp.id ? 'border-orange-500 bg-orange-50' : 'border-slate-100 hover:border-slate-200'
                         }`}
                     >
                         <div className="flex items-center gap-3">
                            <div className="w-10 h-10 bg-white rounded-xl flex items-center justify-center font-bold text-slate-400">
                                {emp.user.username[0].toUpperCase()}
                            </div>
                            <div>
                                <p className="font-bold text-slate-800">{emp.user.username}</p>
                                <p className="text-xs text-slate-500">{emp.designation}</p>
                            </div>
                         </div>
                     </div>
                 ))}
                 {employees.length === 0 && <p className="text-center text-slate-400 py-10">No employees found.</p>}
             </div>
        </div>

        <div className="bg-white p-8 rounded-3xl shadow-xl border border-slate-200">
             <h3 className="text-xl font-bold mb-6">Salary Details</h3>
             <form onSubmit={handleUpdate} className="space-y-6">
                <div className="space-y-2">
                  <label className="text-sm font-semibold text-slate-700 ml-1">Monthly Base Salary ($)</label>
                  <div className="relative group">
                    <div className="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-slate-400 group-focus-within:text-blue-600 transition-colors">
                      <DollarSign size={18} />
                    </div>
                    <input
                        type="number"
                        required
                        className="w-full pl-10 pr-4 py-3 rounded-xl border border-slate-200 focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 outline-none"
                        placeholder="0.00"
                        value={salaryData.amount}
                        onChange={(e) => setSalaryData({ ...salaryData, amount: e.target.value })}
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <label className="text-sm font-semibold text-slate-700 ml-1">Notes / Adjustments</label>
                  <textarea
                    className="w-full px-4 py-3 rounded-xl border border-slate-200 focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 outline-none"
                    placeholder="Bonus, deductions, or other details..."
                    rows={4}
                    value={salaryData.notes}
                    onChange={(e) => setSalaryData({ ...salaryData, notes: e.target.value })}
                  />
                </div>

                <button
                  type="submit"
                  disabled={loading}
                  className="w-full py-4 bg-slate-900 hover:bg-slate-800 text-white font-bold rounded-xl shadow-lg transition-all flex items-center justify-center gap-2"
                >
                  <Save size={20} />
                  <span>{loading ? 'Saving...' : 'Update Payroll'}</span>
                </button>
             </form>
        </div>
      </div>
    </div>
  );
};

export default HRSalaryManagement;
