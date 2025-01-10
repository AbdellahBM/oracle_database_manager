import React, { useState } from 'react';
import { Menu, X } from 'lucide-react';

interface LayoutProps {
  sections: {
    title: string;
    icon: React.ReactNode;
    component: React.ReactNode;
  }[];
}

export function Layout({ sections }: LayoutProps) {
  const [activeSection, setActiveSection] = useState(0);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="fixed inset-y-0 left-0 z-50 w-64 bg-white shadow-lg transform lg:translate-x-0 transition-transform duration-200 ease-in-out">
        <div className="flex items-center justify-between h-16 px-6 bg-indigo-600">
          <h1 className="text-xl font-bold text-white">Oracle Manager</h1>
          <button
            className="lg:hidden text-white"
            onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
          >
            {isMobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>
        <nav className="px-4 py-4">
          {sections.map((section, index) => (
            <button
              key={section.title}
              onClick={() => setActiveSection(index)}
              className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                activeSection === index
                  ? 'bg-indigo-50 text-indigo-600'
                  : 'text-gray-600 hover:bg-gray-50'
              }`}
            >
              {section.icon}
              <span className="font-medium">{section.title}</span>
            </button>
          ))}
        </nav>
      </aside>

      {/* Main content */}
      <main className="lg:pl-64">
        <div className="max-w-7xl mx-auto px-4 py-6">
          <div className="bg-white rounded-lg shadow-sm p-6">
            {sections[activeSection].component}
          </div>
        </div>
      </main>
    </div>
  );
}