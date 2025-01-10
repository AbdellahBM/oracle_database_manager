import React from 'react';
import { Users, UserPlus, UserMinus, Shield } from 'lucide-react';

export function UserManagement() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
        <Users className="w-6 h-6" />
        User Management
      </h2>

      <div className="flex gap-4 mb-8">
        <button className="flex items-center gap-2 px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700">
          <UserPlus className="w-4 h-4" />
          Create User
        </button>
        <button className="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
          <Shield className="w-4 h-4" />
          Manage Roles
        </button>
      </div>

      <div className="bg-gray-50 rounded-lg p-4">
        <table className="min-w-full">
          <thead>
            <tr>
              <th className="text-left py-2">Username</th>
              <th className="text-left py-2">Status</th>
              <th className="text-left py-2">Roles</th>
              <th className="text-left py-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td className="py-2">SYSTEM</td>
              <td className="py-2">
                <span className="px-2 py-1 bg-green-100 text-green-700 rounded-full text-sm">
                  Active
                </span>
              </td>
              <td className="py-2">DBA, SYSDBA</td>
              <td className="py-2">
                <button className="text-red-600 hover:text-red-700">
                  <UserMinus className="w-4 h-4" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}