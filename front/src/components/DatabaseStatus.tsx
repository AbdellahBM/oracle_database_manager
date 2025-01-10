import React from 'react';
import { Database } from 'lucide-react';

export function DatabaseStatus() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
        <Database className="w-6 h-6" />
        Database Status
      </h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <StatusCard
          title="Connection Status"
          value="Connected"
          status="success"
        />
        <StatusCard
          title="Database Role"
          value="Primary"
          status="info"
        />
        <StatusCard
          title="Archive Mode"
          value="ARCHIVELOG"
          status="success"
        />
      </div>
    </div>
  );
}

interface StatusCardProps {
  title: string;
  value: string;
  status: 'success' | 'warning' | 'error' | 'info';
}

function StatusCard({ title, value, status }: StatusCardProps) {
  const statusColors = {
    success: 'bg-green-50 text-green-700 border-green-200',
    warning: 'bg-yellow-50 text-yellow-700 border-yellow-200',
    error: 'bg-red-50 text-red-700 border-red-200',
    info: 'bg-blue-50 text-blue-700 border-blue-200',
  };

  return (
    <div className={`p-4 rounded-lg border ${statusColors[status]}`}>
      <h3 className="font-medium mb-2">{title}</h3>
      <p className="text-lg font-semibold">{value}</p>
    </div>
  );
}