import React from 'react';
import { Shield, Lock, Key } from 'lucide-react';

export function SecuritySection() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
        <Shield className="w-6 h-6" />
        Security Management
      </h2>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        <SecurityCard
          title="TDE Status"
          icon={<Key className="w-5 h-5" />}
          status="Enabled"
          type="success"
        />
        <SecurityCard
          title="Audit Status"
          icon={<Lock className="w-5 h-5" />}
          status="Active"
          type="success"
        />
        <SecurityCard
          title="VPD Policies"
          icon={<Shield className="w-5 h-5" />}
          status="5 Active"
          type="info"
        />
      </div>

      <div className="mt-8">
        <h3 className="text-xl font-semibold mb-4">Audit Log</h3>
        <div className="bg-gray-50 rounded-lg p-4">
          <table className="min-w-full">
            <thead>
              <tr>
                <th className="text-left py-2">Timestamp</th>
                <th className="text-left py-2">Action</th>
                <th className="text-left py-2">User</th>
                <th className="text-left py-2">Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="py-2">2024-03-15 11:45</td>
                <td className="py-2">Login Attempt</td>
                <td className="py-2">SYSTEM</td>
                <td className="py-2 text-green-600">Success</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

interface SecurityCardProps {
  title: string;
  icon: React.ReactNode;
  status: string;
  type: 'success' | 'warning' | 'error' | 'info';
}

function SecurityCard({ title, icon, status, type }: SecurityCardProps) {
  const typeColors = {
    success: 'bg-green-50 text-green-700 border-green-200',
    warning: 'bg-yellow-50 text-yellow-700 border-yellow-200',
    error: 'bg-red-50 text-red-700 border-red-200',
    info: 'bg-blue-50 text-blue-700 border-blue-200',
  };

  return (
    <div className={`p-4 rounded-lg border ${typeColors[type]}`}>
      <div className="flex items-center gap-2 mb-2">
        {icon}
        <h3 className="font-medium">{title}</h3>
      </div>
      <p className="text-lg font-semibold">{status}</p>
    </div>
  );
}