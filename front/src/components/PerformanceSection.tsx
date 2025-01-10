import React from 'react';
import { LineChart, Activity } from 'lucide-react';

export function PerformanceSection() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
        <Activity className="w-6 h-6" />
        Performance Monitoring
      </h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <MetricCard
          title="CPU Usage"
          value="45%"
          trend="up"
          change="+5%"
        />
        <MetricCard
          title="Active Sessions"
          value="23"
          trend="down"
          change="-2"
        />
      </div>

      <div className="mt-8">
        <h3 className="text-xl font-semibold mb-4 flex items-center gap-2">
          <LineChart className="w-5 h-5" />
          Top SQL Queries
        </h3>
        <div className="bg-gray-50 rounded-lg p-4">
          <table className="min-w-full">
            <thead>
              <tr>
                <th className="text-left py-2">SQL ID</th>
                <th className="text-left py-2">Elapsed Time</th>
                <th className="text-left py-2">Executions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="py-2">SQL_01</td>
                <td className="py-2">2.3s</td>
                <td className="py-2">150</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

interface MetricCardProps {
  title: string;
  value: string;
  trend: 'up' | 'down';
  change: string;
}

function MetricCard({ title, value, trend, change }: MetricCardProps) {
  const trendColor = trend === 'up' ? 'text-red-600' : 'text-green-600';

  return (
    <div className="p-4 rounded-lg border border-gray-200">
      <h3 className="font-medium text-gray-600 mb-2">{title}</h3>
      <div className="flex items-end gap-2">
        <span className="text-2xl font-bold">{value}</span>
        <span className={`text-sm ${trendColor}`}>{change}</span>
      </div>
    </div>
  );
}