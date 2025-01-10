import React from 'react';
import { Layout } from './components/Layout';
import { DatabaseStatus } from './components/DatabaseStatus';
import { BackupSection } from './components/BackupSection';
import { PerformanceSection } from './components/PerformanceSection';
import { SecuritySection } from './components/SecuritySection';
import { UserManagement } from './components/UserManagement';
import { DatabaseIcon, ShieldIcon, UserIcon, LineChart, Save } from 'lucide-react';

function App() {
  const sections = [
    {
      title: 'Database Status',
      icon: <DatabaseIcon className="w-6 h-6" />,
      component: <DatabaseStatus />,
    },
    {
      title: 'Backup Management',
      icon: <Save className="w-6 h-6" />,
      component: <BackupSection />,
    },
    {
      title: 'Performance',
      icon: <LineChart className="w-6 h-6" />,
      component: <PerformanceSection />,
    },
    {
      title: 'Security',
      icon: <ShieldIcon className="w-6 h-6" />,
      component: <SecuritySection />,
    },
    {
      title: 'User Management',
      icon: <UserIcon className="w-6 h-6" />,
      component: <UserManagement />,
    },
  ];

  return <Layout sections={sections} />;
}

export default App;