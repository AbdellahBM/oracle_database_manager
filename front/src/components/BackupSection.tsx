import React, { useState } from 'react';
import { Save, History } from 'lucide-react';

export function BackupSection() {
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleBackup = async (type: string) => {
    setIsLoading(true);
    setMessage('');
    try {
      const endpoint = type === 'full' 
        ? '/oracle/backup/full'
        : type === 'incremental'
          ? '/oracle/backup/incremental'
          : '/oracle/backup/tablespace/users';

      const response = await fetch(`http://localhost:8080${endpoint}`, {
        method: 'POST',
      });
      
      const data = await response.text();
      setMessage(`${type.charAt(0).toUpperCase() + type.slice(1)} backup completed: ${data}`);
    } catch (error) {
      setMessage(`Error performing ${type} backup: ${error}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
        <Save className="w-6 h-6" />
        Backup Management
      </h2>
      
      {message && (
        <div className={`mb-4 p-4 rounded-lg ${message.includes('Error') ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>
          {message}
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        <BackupCard
          title="Full Backup"
          description="Perform a complete database backup"
          onClick={() => handleBackup('full')}
          disabled={isLoading}
        />
        <BackupCard
          title="Incremental Backup"
          description="Backup changes since last full backup"
          onClick={() => handleBackup('incremental')}
          disabled={isLoading}
        />
        <BackupCard
          title="Archive Log Backup"
          description="Backup archive logs only"
          onClick={() => handleBackup('archive')}
          disabled={isLoading}
        />
      </div>

      <div className="mt-8">
        <h3 className="text-xl font-semibold mb-4 flex items-center gap-2">
          <History className="w-5 h-5" />
          Backup History
        </h3>
        <div className="bg-gray-50 rounded-lg p-4">
          <table className="min-w-full">
            <thead>
              <tr>
                <th className="text-left py-2">Date</th>
                <th className="text-left py-2">Type</th>
                <th className="text-left py-2">Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="py-2">2024-03-15 10:30</td>
                <td className="py-2">Full</td>
                <td className="py-2 text-green-600">Completed</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

interface BackupCardProps {
  title: string;
  description: string;
  onClick: () => void;
  disabled: boolean;
}

function BackupCard({ title, description, onClick, disabled }: BackupCardProps) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={`p-4 rounded-lg border border-gray-200 hover:border-indigo-300 hover:bg-indigo-50 transition-colors text-left ${
        disabled ? 'opacity-50 cursor-not-allowed' : ''
      }`}
    >
      <h3 className="font-semibold mb-2">{title}</h3>
      <p className="text-sm text-gray-600">{description}</p>
    </button>
  );
}