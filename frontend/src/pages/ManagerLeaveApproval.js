import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Calendar, Check, X, Clock } from 'lucide-react';

const ManagerLeaveApproval = () => {
  const [leaves, setLeaves] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchLeaves();
  }, []);

  const fetchLeaves = async () => {
    try {
      const res = await api.get('/manager/leaves/pending');
      setLeaves(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleReview = async (id, approve) => {
    const action = approve ? 'approve' : 'reject';
    try {
      await api.post(`/manager/leave/${action}/${id}`);
      fetchLeaves();
    } catch (err) {
      alert('Error reviewing leave');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-900">Final Leave Approvals</h1>
        <p className="text-slate-500">Review requests already cleared by HR.</p>
      </div>

      <div className="grid grid-cols-1 gap-6">
        {leaves.map((leave) => (
          <div key={leave.id} className="bg-white p-6 rounded-3xl shadow-sm border border-slate-200 flex flex-col md:flex-row md:items-center justify-between gap-6 hover:shadow-md transition-shadow border-l-8 border-l-blue-500">
            <div className="flex items-center gap-5">
              <div className="w-14 h-14 bg-blue-50 text-blue-600 rounded-2xl flex items-center justify-center font-bold text-xl">
                 {leave.employee.user.username[0].toUpperCase()}
              </div>
              <div>
                <h3 className="font-bold text-slate-900 text-lg">{leave.employee.user.username}</h3>
                <p className="text-slate-500 text-sm font-medium">{leave.employee.designation}</p>
                <div className="flex items-center gap-2 mt-2 text-slate-400 text-xs font-bold uppercase tracking-wider">
                   <Clock size={14} />
                   <span>{leave.startDate} to {leave.endDate}</span>
                </div>
              </div>
            </div>

            <div className="flex-1 px-4">
                <span className="text-xs font-bold text-blue-600 uppercase mb-1 block tracking-tight">HR Approved Reason:</span>
                <p className="text-slate-600 italic">"{leave.reason}"</p>
            </div>

            <div className="flex items-center gap-3">
              <button
                onClick={() => handleReview(leave.id, false)}
                disabled={loading}
                className="p-3 bg-red-50 text-red-600 rounded-2xl hover:bg-red-100 transition-colors"
              >
                <X size={24} />
              </button>
              <button
                onClick={() => handleReview(leave.id, true)}
                disabled={loading}
                className="p-3 bg-green-50 text-green-600 rounded-2xl hover:bg-green-100 transition-colors"
                title="Final Approval"
              >
                <Check size={24} />
              </button>
            </div>
          </div>
        ))}
        {leaves.length === 0 && (
          <div className="text-center py-24 bg-white rounded-3xl border border-dashed border-slate-300">
             <Calendar size={48} className="mx-auto text-slate-200 mb-4" />
             <p className="text-slate-400 font-medium">No pending leave requests for final approval</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default ManagerLeaveApproval;
