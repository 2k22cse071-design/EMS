import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Plus, Building } from 'lucide-react';

const Departments = () => {
  const [departments, setDepartments] = useState([]);
  const [newName, setNewName] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    const res = await api.get('/departments');
    setDepartments(res.data);
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!newName) return;
    setLoading(true);
    try {
      await api.post('/departments', { name: newName });
      setNewName('');
      fetchDepartments();
    } catch (err) {
      alert('Error creating department');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">Departments</h1>
          <p className="text-slate-500">Manage organizational units</p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div className="md:col-span-1">
          <div className="bg-white p-6 rounded-3xl shadow-sm border border-slate-200 sticky top-8">
            <h3 className="text-lg font-bold mb-4">Add New</h3>
            <form onSubmit={handleCreate} className="space-y-4">
              <input
                type="text"
                placeholder="Department Name"
                className="w-full px-4 py-3 rounded-xl border border-slate-200 focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 outline-none"
                value={newName}
                onChange={(e) => setNewName(e.target.value)}
              />
              <button
                type="submit"
                disabled={loading}
                className="w-full py-3 bg-blue-600 hover:bg-blue-500 text-white font-bold rounded-xl shadow-lg shadow-blue-600/20 transition-all flex items-center justify-center gap-2"
              >
                <Plus size={18} />
                <span>Add</span>
              </button>
            </form>
          </div>
        </div>

        <div className="md:col-span-2 space-y-4">
          {departments.map((dept) => (
            <div key={dept.id} className="bg-white p-6 rounded-3xl shadow-sm border border-slate-200 flex items-center justify-between group">
              <div className="flex items-center gap-4">
                <div className="p-3 bg-slate-100 text-slate-600 rounded-2xl group-hover:bg-blue-50 group-hover:text-blue-600 transition-colors">
                  <Building size={20} />
                </div>
                <span className="font-bold text-slate-800 text-lg">{dept.name}</span>
              </div>
            </div>
          ))}
          {departments.length === 0 && (
            <div className="text-center py-12 bg-white rounded-3xl border border-dashed border-slate-300">
               <p className="text-slate-400">No departments created yet.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Departments;
