package Fixtures::Integration::DeliveryserviceRegex;
#
# Copyright 2015 Comcast Cable Communications Management, LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
use Moose;
extends 'DBIx::Class::EasyFixture';
use namespace::autoclean;

my %definition_for = ();

sub gen_data {
	foreach my $i ( 1, 2, 3, 4, 11, 12, 13, 14 ) {
		$definition_for{ $i . "_" . $i } = {
			new   => 'DeliveryserviceRegex',
			using => {
				deliveryservice => $i,
				regex           => $i,
				set_number      => 0
			},
		};
	}
}

sub name {
	return "DeliveryserviceRegex";
}

sub get_definition {
	my ( $self, $name ) = @_;
	return $definition_for{$name};
}

sub all_fixture_names {
	return keys %definition_for;
}

__PACKAGE__->meta->make_immutable;

1;
