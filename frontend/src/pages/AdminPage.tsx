import React, { useEffect, useState } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Input,
  Table,
  TableHeader,
  TableBody,
  TableColumn,
  TableRow,
  TableCell,
} from "@nextui-org/react";
import { Trash2 } from "lucide-react";
import {
  apiService,
  AdminStats,
  AdminUserResponse,
  AdminUserCreateRequest,
} from "../services/apiService";

const AdminPage: React.FC = () => {
  const [stats, setStats] = useState<AdminStats | null>(null);
  const [users, setUsers] = useState<AdminUserResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    fetchAdminData();
  }, []);

  const fetchAdminData = async () => {
    try {
      setLoading(true);
      const [statsData, usersData] = await Promise.all([
        apiService.getAdminStats(),
        apiService.getAdminUsers(),
      ]);
      setStats(statsData);
      setUsers(usersData);
      setError(null);
    } catch (err) {
      setError("Failed to load admin data. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateUser = async () => {
    if (!name.trim() || !email.trim() || !password.trim()) {
      setError("Please fill in name, email and password.");
      return;
    }

    setError(null);
    setIsSubmitting(true);

    try {
      const newUser: AdminUserCreateRequest = {
        name: name.trim(),
        email: email.trim(),
        password: password.trim(),
      };
      await apiService.createAdminUser(newUser);
      setName("");
      setEmail("");
      setPassword("");
      await fetchAdminData();
    } catch (err) {
      setError("Failed to create user. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleDeleteUser = async (id: string) => {
    const confirmed = window.confirm("Delete this user?");
    if (!confirmed) {
      return;
    }

    try {
      setLoading(true);
      await apiService.deleteAdminUser(id);
      await fetchAdminData();
    } catch (err) {
      setError("Failed to delete user. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-4 space-y-6">
      <div className="grid gap-4 md:grid-cols-3">
        <Card>
          <CardHeader>
            <h2 className="text-lg font-semibold">Toplam Post</h2>
          </CardHeader>
          <CardBody>
            <p className="text-3xl font-bold">{stats ? stats.postCount : "--"}</p>
          </CardBody>
        </Card>
        <Card>
          <CardHeader>
            <h2 className="text-lg font-semibold">Toplam Kategori</h2>
          </CardHeader>
          <CardBody>
            <p className="text-3xl font-bold">{stats ? stats.categoryCount : "--"}</p>
          </CardBody>
        </Card>
        <Card>
          <CardHeader>
            <h2 className="text-lg font-semibold">Toplam Etiket</h2>
          </CardHeader>
          <CardBody>
            <p className="text-3xl font-bold">{stats ? stats.tagCount : "--"}</p>
          </CardBody>
        </Card>
      </div>

      <Card>
        <CardHeader className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h1 className="text-2xl font-bold">Admin Paneli</h1>
            <p className="text-sm text-default-500">
              Kullanıcı ekle, sil ve sitenin temel istatistiklerini gör.
            </p>
          </div>
        </CardHeader>
        <CardBody>
          {error && (
            <div className="mb-4 rounded-lg bg-red-50 p-4 text-red-700">
              {error}
            </div>
          )}

          <div className="grid gap-4 lg:grid-cols-[1.2fr_1fr]">
            <div className="space-y-4">
              <div className="rounded-lg border border-default-200 bg-default-50 p-4">
                <h2 className="mb-3 text-lg font-semibold">Yeni Kullanıcı Oluştur</h2>
                <div className="space-y-3">
                  <Input
                    label="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    fullWidth
                    placeholder="Admin name"
                  />
                  <Input
                    type="email"
                    label="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    fullWidth
                    placeholder="user@example.com"
                  />
                  <Input
                    type="password"
                    label="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    fullWidth
                    placeholder="Password"
                  />
                  <Button
                    onPress={handleCreateUser}
                    isLoading={isSubmitting}
                    color="primary"
                  >
                    Create User
                  </Button>
                </div>
              </div>

              <div className="rounded-lg border border-default-200 bg-default-50 p-4">
                <h2 className="mb-3 text-lg font-semibold">Notlar</h2>
                <p className="text-sm text-default-500">
                  Bu sayfa admin servislerini kullanır: kullanıcı listesi, kullanıcı oluşturma ve silme.
                </p>
              </div>
            </div>

            <div className="rounded-lg border border-default-200 bg-default-50 p-4">
              <h2 className="mb-3 text-lg font-semibold">Kullanıcılar</h2>
              <Table aria-label="Admin users table" isCompact>
                <TableHeader>
                  <TableColumn>Name</TableColumn>
                  <TableColumn>Email</TableColumn>
                  <TableColumn>Created At</TableColumn>
                  <TableColumn>Actions</TableColumn>
                </TableHeader>
                <TableBody isLoading={loading} loadingContent={<div>Loading users...</div>}>
                  {users.map((user) => (
                    <TableRow key={user.id}>
                      <TableCell>{user.name}</TableCell>
                      <TableCell>{user.email}</TableCell>
                      <TableCell>{new Date(user.createdAt).toLocaleString()}</TableCell>
                      <TableCell>
                        <Button
                          isIconOnly
                          variant="flat"
                          color="danger"
                          onClick={() => handleDeleteUser(user.id)}
                        >
                          <Trash2 size={16} />
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </div>
        </CardBody>
      </Card>
    </div>
  );
};

export default AdminPage;
